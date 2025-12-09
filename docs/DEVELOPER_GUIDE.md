# Developer Guide

Welcome to the SmartTube Developer Guide! This guide is intended for those who wish to contribute to the project or build the application from source.

## Table of Contents
- [Team](#team)
- [Build Instructions](#build-instructions)
- [Contributing](#contributing)

## Team

SmartTube is developed single-handedly; there is no larger team or company behind this. This is an open source, hobby project. Several others have helped with translations, some of which can be seen on [Github](https://github.com/yuliskov/SmartTube/graphs/contributors), some have sent their translations directly to Yurii. There are also helpful people in the support chat.

## Build Instructions

**NOTE: OpenJDK 14 or older (!) is required. Newer JDK could cause app crash!**

To build and install debug version, run these commands:

```bash
git clone https://github.com/yuliskov/SmartTube.git
cd SmartTube
git submodule update --init
adb connect <device_ip_address>
gradlew clean installStstableDebug
```

## Contributing

If you're interested in contributing to SmartTube, please check the [Issue Tracker](https://github.com/yuliskov/SmartTube/issues) for open issues. You can also join the discussion in our Telegram or Discord groups.

- **Telegram group (international)**: [@SmartTubeEN](http://t.me/SmartTubeEN)
- **Discord group (international)**: [SmartTube Official](https://discord.gg/Wt8HDDej5z)
