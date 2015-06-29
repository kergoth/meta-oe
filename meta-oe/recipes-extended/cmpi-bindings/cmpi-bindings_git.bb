SUMMARY = "Adapter to write and run CMPI-type CIM providers"
DESCRIPTION = "CMPI-compliant provider interface for various languages via SWIG"
HOMEPAGE = "http://github.com/kkaempf/cmpi-bindings"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b19ee058d2d5f69af45da98051d91064"
SECTION = "Development/Libraries"
DEPENDS = "swig-native python sblim-cmpi-devel"

SRC_URI = "git://github.com/kkaempf/cmpi-bindings.git;protocol=http \
           file://cmpi-bindings-0.4.17-no-ruby-perl.patch \
           file://cmpi-bindings-0.4.17-sblim-sigsegv.patch \
           file://cmpi-bindings-0.9.5-python-lib-dir.patch \
           file://0001-Modify-cmakelist.patch \
           file://0001-Fix-error.patch"
SRCREV = "93b6044f53bcfa79253d7af51a9c23ddd73f8486"
S = "${WORKDIR}/git"

inherit cmake pythonnative

EXTRA_OECMAKE = "-DLIB='${baselib}'"

do_configure_prepend() {
    export HOST_SYS=${HOST_SYS}
    export BUILD_SYS=${BUILD_SYS}
    export STAGING_LIBDIR=${STAGING_LIBDIR}
    export STAGING_INCDIR=${STAGING_INCDIR}
    export ENV_INSTALL_PATH=${PYTHON_SITEPACKAGES_DIR}
}

FILES_${PN} =+"${libdir}/cmpi/libpyCmpiProvider.so ${PYTHON_SITEPACKAGES_DIR}/*"
FILES_${PN}-dbg =+ "${libdir}/cmpi/.debug/libpyCmpiProvider.so"
