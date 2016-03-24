# MineAPI
An API for Spigot/Glowstone and more!

[![Build Status](https://drone.io/github.com/w67clement/MineAPI/status.png)](https://drone.io/github.com/w67clement/MineAPI/latest)

## Add MineAPI to your Maven/Gradle repository
### Maven
Hello, I'm sorry but Maven isn't supported :/
### Gradle
Put in repositories section: <br />
<pre class="language-java"><code class="language-java">maven {
    url "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
}
maven {
    url "http://repo.glowstone.net/content/repositories/snapshots/"
}
maven {
    url "https://oss.sonatype.org/content/repositories/snapshots"
}
ivy {
    url "http://www.tcpr.ca/files/"
    layout 'pattern', {
        artifact '[module]/[module]-[revision].[ext]'
    }
}
maven {
    url "https://dl.bintray.com/w67clement/maven"
}
mavenCentral();
jcenter();</code></pre>
And put in dependencies section: <br />
`compile 'com.w67clement.mineapi:mineapi:2.2.0-dev4'`
     

## Clone and modify MineAPI
Ho, you want modify MineAPI? Clone the project into a directory, if:
### You are on Windows
Open Cmd in the working directory and execute: <br />
Whether you use [Eclipse](http://www.eclipse.org/), execute `gradlew eclipse` and Gradle setup the Workspace for Eclipse! <br />
Whether you use [Intellij IDEA](https://www.jetbrains.com/idea/), execute `gradlew idea` and Gradle setup the Workspace for IDEA! <br />
Now you can modify MineAPI! <br />
And build? <br />
It's easy! You just execute `gradlew build` and Gradle compile MineAPI!
### You are on Linux/Mac
Open Terminal in the working directory and execute: <br />
Whether you use [Eclipse](http://www.eclipse.org/), execute `./gradlew eclipse` and Gradle setup the Workspace for Eclipse! <br />
Whether you use [Intellij IDEA](https://www.jetbrains.com/idea/), execute `./gradlew idea` and Gradle setup the Workspace for IDEA! <br />
If Terminal say `Permission denied`, you need execute `chmod +x gradlew` before. <br />
Now you can modify MineAPI! <br />
And build? <br />
It's easy! You just execute `./gradlew build` and Gradle compile MineAPI!
### Requirements
You need Java 8 for compile MineAPI and modify it!

## Builds
On drone.io: [MineAPI](https://drone.io/github.com/w67clement/MineAPI)
[![Build Status](https://drone.io/github.com/w67clement/MineAPI/status.png)](https://drone.io/github.com/w67clement/MineAPI/latest)