resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.0")

// web plugins

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.1.0")