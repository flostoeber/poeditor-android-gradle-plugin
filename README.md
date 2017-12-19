# Poeditor Android Gradle Plugin
Simple plugin that eases importing PoEditor localized strings to your Android project.

What is PoEditor? [Check it out](https://poeditor.com)

Download
--------

In your main build.gradle, buildscript block, add [jitpack.io](https://jitpack.io/) to the repositories and include the plugin as a dependency:
```groovy
buildscript {
    repositories { 
        maven { url 'https://jitpack.io' }
        ...
    }
    dependencies {
        ...
        classpath 'com.github.flostoeber:poeditor-android-gradle-plugin:-SNAPSHOT'
}
```

Enjoy!

Configuration
--------
Apply and configure the plugin to your app's module build.gradle file.

```groovy
apply plugin: 'com.bq.poeditor'

poEditorPlugin.api_token = <poeditor_api_token>
poEditorPlugin.project_id = <poeditor_project_id> 
poEditorPlugin.default_lang = "en"
poEditorPlugin.res_dir_path = "${project.rootDir}/app/src/main/res"
```

The complete attribute list:

Attribute                     | Description
------------------------------|-----------------------------------------
```api_token```               | Poeditor API Token.
```project_id```              | Poeditor project ID.
```default_lang```            | The lang to be used to build default ```strings.xml``` (```/values``` folder)
```res_dir_path```            | The path to the project's ```/res``` folder.

If you want to customize another property open a PR or leave a comment!

Usage
--------
Just run the new ```importPoEditorStrings``` task via Android Studio or command line:

```
./gradlew importPoEditorStrings
```

This task will:
- download all strings files (every available lang) from PoEditor given the api token and project id.
- process the incoming strings to fix some PoEditor incompatibilities with Android strings system. 
- create and save strings.xml files to ```/values-<lang>``` (or ```/values``` in case of the default lang). It supports
region specific languages by creating the proper folders (i.e. ```/values-es-rMX```).

License
-------
This project is licensed under the Apache Software License, Version 2.0.

    Copyright (c) 2016 bq

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
