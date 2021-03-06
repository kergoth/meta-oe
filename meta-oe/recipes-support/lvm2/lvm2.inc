SECTION = "utils"
DESCRIPTION = "LVM2 is a set of utilities to manage logical volumes in Linux."
DEPENDS = "udev"
LICENSE = "GPLv2 & LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f \
                    file://COPYING.LIB;md5=fbc093901857fcd118f065f900982c24"
INC_PR = "r4"


S = "${WORKDIR}/LVM2.${PV}"
SRC_URI = "ftp://sources.redhat.com/pub/lvm2/old/LVM2.${PV}.tgz \
           file://0001-Replace-CPPFunction-with-rl_completion_func_t.patch \
           file://mlock-ignore-vectors-gate_vma.patch \
           file://lvm.conf \
           file://0001-snapshot-check-snapshot-exists.patch"

PACKAGECONFIG ??= "readline"
PACKAGECONFIG[readline] = "--enable-readline,--disable-readline,readline"

# Unset user/group to unbreak install.
EXTRA_OECONF = "--with-user= \
                --with-group= \
                --disable-o_direct \
                --enable-realtime \
                --enable-applib \
                --enable-cmdlib \
                --enable-udev_sync \
                --enable-udev_rules \
                --enable-pkgconfig \
                --enable-dmeventd \
                --with-udev-prefix= \
                --with-usrlibdir=${libdir} \
"

PACKAGECONFIG += "${@base_contains('DISTRO_FEATURES', 'selinux', 'selinux', '', d)}"

PACKAGECONFIG[selinux] = "--enable-selinux,--disable-selinux,libselinux,"

inherit autotools pkgconfig systemd

do_install_append() {
    # Install machine specific configuration file
    install -m 0644 ${WORKDIR}/lvm.conf ${D}${sysconfdir}/lvm/lvm.conf
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${B}/scripts/lvm2_monitoring_systemd_red_hat.service ${D}${systemd_unitdir}/system/lvm2-monitor.service
    install -m 0644 ${B}/scripts/dm_event_systemd_red_hat.socket ${D}${systemd_unitdir}/system/dm-event.socket
    install -m 0644 ${B}/scripts/dm_event_systemd_red_hat.service ${D}${systemd_unitdir}/system/dm-event.service
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "lvm2-monitor.service dm-event.socket dm-event.service"
SYSTEMD_AUTO_ENABLE = "disable"

TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} += "${libdir}/device-mapper/*.so ${base_libdir}/udev"
FILES_${PN}-dbg += "${libdir}/device-mapper/.debug"

RDEPENDS_${PN} = "bash"

CONFFILES_${PN} += "${sysconfdir}/lvm/lvm.conf"
