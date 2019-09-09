# extractor challenge
Extract pattern matches from main page of sites

This is a Spring Shell project, the only requirement to use this project 
is have installed Java 1.8+.

The definition of the dependencies for this project you can be found in 
build.gradle the use of Lombok in IDE must be enabled if you want to use in this tool.

# installation

```bash
// Inside the root folder, please run those commands

// Build the artifact to execute (gradlew - UNIX or gradlew.bat - WINDOWS)
./gradlew clean build

/Run the artifact in the current terminal
java -jar build/libs/extractor-0.0.1-SNAPSHOT.jar
```

After that you can see the spring shell developed for this challenge.

This shell supports different commands. At the beginning you can use the command 
`help` to see what are the supported commands

For the first use, you need to create a file with the input of the sites 
list to apply the data extraction, this file can be created in any location in your computer.

An example of the file you need is this:
```vi
https://www.oberlo.com/blog/best-instagram-hashtags-for-likes
https://twitter.com/i/moments
https://getdaytrends.com/
https://www.trendinalia.com/twitter-trending-topics/globales/globales-190907.html
```

When you call the `extract` command for a specific file take into account: 
* If your OS is Windows you need to use it in this way: 
**C:\\Users\\userName\\desktop\\list.txt**
* If your OS is Linux or OSX you need to use it in this way: 
**/home/userName/desktop/list.txt**

In the next code block you can see an example of a correct execution and a possible 
result in debug mode for logger.
```bash
EXTRACTOR:>extract -fl /home/jhonatanz/Desktop/list.txt
2019-09-09 12:29:07.075 ERROR 22993 --- [pool-1-thread-4] c.c.e.s.generator.HashTagGenerator       : Illegal character in path at index 81: https://www.trendinalia.com/twitter-trending-topics/globales/globales-190907.html<Paste> is an unknown host 
2019-09-09 12:29:07.872 DEBUG 22993 --- [pool-1-thread-1] c.c.e.s.generator.HashTagGenerator       : site: https://www.oberlo.com/blog/best-instagram-hashtags-for-likes - length: 40382 
2019-09-09 12:29:07.879 DEBUG 22993 --- [pool-1-thread-1] c.c.e.s.generator.HashTagGenerator       : 193 matches found in url https://www.oberlo.com/blog/best-instagram-hashtags-for-likes 
2019-09-09 12:29:08.070 DEBUG 22993 --- [pool-1-thread-3] c.c.e.s.generator.HashTagGenerator       : site: https://getdaytrends.com/ - length: 15050 
2019-09-09 12:29:08.073 DEBUG 22993 --- [pool-1-thread-3] c.c.e.s.generator.HashTagGenerator       : 25 matches found in url https://getdaytrends.com/ 
2019-09-09 12:29:08.105 DEBUG 22993 --- [pool-1-thread-2] c.c.e.s.generator.HashTagGenerator       : site: https://twitter.com/i/moments - length: 22244 
2019-09-09 12:29:08.107 DEBUG 22993 --- [pool-1-thread-2] c.c.e.s.generator.HashTagGenerator       : 0 matches found in url https://twitter.com/i/moments 
2019-09-09 12:29:08.108 DEBUG 22993 --- [onPool-worker-1] c.c.e.s.c.ProcessParallelManager         : https://www.oberlo.com/blog/best-instagram-hashtags-for-likes 
2019-09-09 12:29:08.109 DEBUG 22993 --- [onPool-worker-1] c.c.e.s.c.ProcessParallelManager         : https://twitter.com/i/moments 
2019-09-09 12:29:08.109 DEBUG 22993 --- [onPool-worker-1] c.c.e.s.c.ProcessParallelManager         : https://getdaytrends.com/ 
2019-09-09 12:29:08.109 DEBUG 22993 --- [onPool-worker-1] c.c.e.s.c.ProcessParallelManager         : https://www.trendinalia.com/twitter-trending-topics/globales/globales-190907.html<Paste> 
Show the inputs in this path /home/jhonatanz/Desktop/ with the matches found in all sites 
Cases success :: 3 
Cases failed :: 1 
```
