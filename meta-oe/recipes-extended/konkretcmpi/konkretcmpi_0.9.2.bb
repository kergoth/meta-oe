SUMMARY = "Tool for rapid CMPI providers development"
DESCRIPTION = "\
KonkretCMPI makes CMPI provider development easier by generating type-safe \
concrete CIM interfaces from MOF definitions and by providing default \
implementations for many of the provider operations."
HOMEPAGE = "https://github.com/rnovacek/konkretcmpi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=f673270bfc350d9ce1efc8724c6c1873"
DEPENDS = "swig sblim-cmpi-devel python cmake-native"

SRC_URI = "https://github.com/rnovacek/${BPN}/archive/${PV}/${BP}.tar.gz \
           file://konkretcmpi-0.9.2-fix-returning-instance-from-method.patch"
SRC_URI[md5sum] = "7e8ed4f47d1a9e5cbed4208920f89d64"
SRC_URI[sha256sum] = "2ac52fc374e46d68317194bbd6b44e0b2f934df24b201efb395d3eccf0fed634"

inherit cmake

EXTRA_OECMAKE = "-DWITH_PYTHON=ON"

do_install_append() {
    if [ -d ${D}${prefix}/lib64 ]; then
        mv ${D}${prefix}/lib64 ${D}${libdir}
    fi

    rm -rf ${D}${datadir}
}

PACKAGES =+ "${PN}-python ${PN}-python-dbg"

FILES_${PN}-python = "${libdir}/python2.7/site-packages/konkretmof.py* ${libdir}/python2.7/site-packages/_konkretmof.so"
FILES_${PN}-python-dbg = "${libdir}/python2.7/site-packages/.debug/*"

BBCLASSEXTEND = "native"
