# Student Sorter Application

A JavaFX application for sorting student records.

## Requirements

- JDK 21 or newer

## Building the Application

### Build a standalone application image

```bash
./gradlew jpackageImage
```

This will create a standalone application image in the `app/build/jpackage/StudentSorter` directory. You can run the application by executing `StudentSorter.exe` in that directory.

### Build a runtime image without application launcher

```bash
./gradlew jlink
```

This will create a custom Java runtime with your application in the `app/build/image` directory. You can run the application using the provided launcher scripts:

```bash
# On Windows
app/build/image/bin/StudentSorter.bat

# On Linux/macOS
app/build/image/bin/StudentSorter
```

### Building a native installer (requires additional tools)

To build a native installer package (MSI on Windows, DMG on macOS, DEB/RPM on Linux), you need to:

1. For Windows: Install WiX Toolset from https://wixtoolset.org/ and add it to your PATH
2. Update the build.gradle file to enable installer creation:
   ```groovy
   jpackage {
       // Set skipInstaller to false
       skipInstaller = false
       
       // Configure installer type based on platform
       if (org.gradle.internal.os.OperatingSystem.current().isWindows()) {
           installerType = 'msi'
           installerOptions = ['--win-dir-chooser', '--win-menu', '--win-shortcut']
       } else if (org.gradle.internal.os.OperatingSystem.current().isMacOsX()) {
           installerType = 'dmg'
           installerOptions = ['--mac-package-name', 'StudentSorter']
       } else {
           installerType = 'deb'
           installerOptions = ['--linux-shortcut']
       }
   }
   ```
3. Run the jpackage task:
   ```bash
   ./gradlew jpackage
   ```

## Development

To run the application during development:

```bash
./gradlew run
```

## Benefits of jpackage over shadowJar

- Creates a standalone application image with a native launcher
- Bundles a custom Java runtime with only the modules your application needs
- Provides desktop integration (shortcuts, icons, etc.) when creating installers
- No need for users to have Java installed
- Better user experience for installation and launching
- More maintainable solution with modern Java tooling