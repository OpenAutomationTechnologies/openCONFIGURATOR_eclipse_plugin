openCONFIGURATOR Core {#page_openconfig_core_lib}
==================

# Build openCONFIGURATOR Core_library:
------------------------------

### Pre-requisites:

- JDK of version 1.7 or above.
- Swig of version 3.0.3 or above.
- Boost libraries of version 1.58.0
- CMake of version 2.8.12 or above
- Microsoft Visual Studio 2012 version 11.0

### openCONFIGURATOR Core library compilation steps:

Note: .* indicates source parent folder of openCONFIGURATOR library

#### Windows x86:

- Download "Boost 1.58.0".
- Locate the boost libraries as "C:\Program Files\Boost\boost\_1\_58\_0_x86"
- From the boost root-directory run bootstrap.bat
- From the boost root-directory run following command to compile Boost.Log and its dependent libraries:

  b2 variant=debug,release link=shared threading=multi runtime-link=shared --with-log address-model=32 toolset=msvc --stagedir=./stage/win32

- This will create dll's and their import-libraries in ./stage/lib 

- Open the VS2012 x86 Native tools command prompt and execute the following:
- From the build directory of  library (.\*/build/Windows/x86) execute the following commands 
  call "C:\Program Files (x86)\Microsoft Visual Studio 11.0\VC\vcvarsall.bat" x86

 - set BOOST\_ROOT=C:\Program Files\Boost\boost\_1\_58\_0\_x86
 - set BOOST\_LIBRARYDIR=C:\Program Files\Boost\boost\_1\_58\_0\_x86\libs
 - cmake.exe -G "Visual Studio 11" -DCMAKE\_INSTALL\_DIR=.*\bin\windows\x86 -DCMAKE\_BUILD\_TYPE=Release -DOPEN\_CONFIGURATOR\_CORE\_JAVA\_WRAPPER=ON ../../..
 - cmake.exe --build . --target install --config Release
  
- The .dll and the wrapper jar files will be generated in the bin directory.

#### Windows x86_64:

- Download "Boost 1.58.0".
- Locate the boost libraries as "C:\Program Files\Boost\boost\_1\_58\_0\_x86_64"
- From the boost root-directory run bootstrap.bat
- From the boost root-directory run following command to compile Boost.Log and its dependent libraries:


  b2 variant=debug,release link=shared threading=multi runtime-link=shared --with-log address-model=64 toolset=msvc --stagedir=./stage/x64

- This will create dll's and their import-libraries in ./stage/lib 	


- Open the VS2012 x64 Cross tools command prompt and execute the following:
- From the build directory of library (.*/build/Windows/x86_64) execute the following commands 
  call "C:\Program Files (x86)\Microsoft Visual Studio 11.0\VC\vcvarsall.bat" x86\_amd64

   - set BOOST\_ROOT=C:\Program Files\Boost\boost\_1\_58\_0\_x86\_64
   - set BOOST\_LIBRARYDIR=C:\Program Files\Boost\boost\_1\_58\_0\_x86\_64\libs
   - cmake.exe -G "Visual Studio 11 Win64" -DCMAKE\_INSTALL\_DIR=.*\bin\windows\x86\_64 -DCMAKE\_BUILD\_TYPE=Release -DOPEN\_CONFIGURATOR\_CORE\_JAVA\_WRAPPER=ON ../../..
   - cmake.exe --build . --target install --config Release	
  
- The .dll and the wrapper jar files will be generated in the bin directory.
		
#### Linux x86:

- In the command terminal Execute the following commands:
- From the build directory of library (.\*/build/linux/x86) execute
    - cmake -DCMAKE\_INSTALL\_PREFIX=.*/core_lib/bin/linux/$ubuntuArch -DCMAKE\_BUILD\_TYPE=Debug -DOPEN\_CONFIGURATOR\_CORE\_JAVA\_WRAPPER=ON ../..
    - make
    - make install
  
- The .so files will be generated at the bin directory.
  
#### Linux x86_64:

- In the command terminal Execute the following commands:
- From the build directory of library (.\*/build/linux/x86_amd64) execute
    - cmake -DCMAKE\_INSTALL\_PREFIX=.*/core_lib/bin/linux/$ubuntuArch -DCMAKE\_BUILD\_TYPE=Debug -DOPEN\_CONFIGURATOR\_CORE\_JAVA\_WRAPPER=ON ../..
    - make
    - make install

- The .so files will be generated at the bin directory.