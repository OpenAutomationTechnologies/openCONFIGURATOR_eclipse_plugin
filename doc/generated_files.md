Generated files {#page_openconfig_generated_files}
================

openCONFIGURATOR creates four files which can be used by the openPOWERLINK
stack and application:

* `mnobd.cdc`

  This file is used to configure the MN stack. It includes all
  configuration data of the MN and all CNs including the network mapping
  information. CN configuration is handled by the configuration manager (CFM)
  module of the MN.

* `mnobd.txt`

  This file describes the stack configuration in human-readable format. It
  includes all configuration data of the MN and all CNs including the network
  mapping information. This file is provided for diagnostic purposes only.

* `xap.xml`

  The xml file contains the structure definition of the process image. It
  depends on the available data fields of the CNs used in the application. The
  application can parse the xml file and therefore get information about the
  mapped channel offsets within the process image.

* `xap.h`

  The header file contains structure definition of the process image in the
  form of two ANSI C structures. It can be directly included in an application
  such as the openPOWERLINK stack demos.

