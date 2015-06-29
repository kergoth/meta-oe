SUMMARY = "CIM providers for network management"
DESCRIPTION = "\
openlmi-networking is set of CMPI providers for network management using \
Common Information Model (CIM)."
HOMEPAGE = "http://www.openlmi.org/"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=7c13b3376cea0ce68d2d2da0a1b3a72c"
SECTION = "System/Management"
DEPENDS = "openlmi-providers konkretcmpi sblim-cmpi-devel cim-schema-exper networkmanager dbus libcheck glib-2.0"

SRC_URI = "http://fedorahosted.org/released/${BPN}/${BP}.tar.gz \
          "
SRC_URI[md5sum] = "f20de8c76fb6a80001b14c1eb035953e"
SRC_URI[sha256sum] = "578eaa5c65fe924b5d7aeb635509dd46443166cd6a88b019bc42646e3518a460"

inherit cmake

do_configure_prepend() {
    export STAGING_DATADIR="${STAGING_DATADIR}"
}

do_install_append() {
    if [ -d ${D}${prefix}/lib64 ]; then
        mv ${D}${prefix}/lib64 ${D}${libdir}
    fi
}

FILES_${PN} =+ "${libdir}/cmpi/libcmpiLMI_Networking.so ${prefix}/libexec*"
FILES_${PN}-dbg =+ "${libdir}/cmpi/.debug*"
