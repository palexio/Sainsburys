#Sainsbury's test


#####The system depends on an externnal library downloaded from https://www.teamdev.com/jxbrowser


#####install MAVEN


#####install the external libraries in the maven repository:
jars are provided in lib folder

    mvn install:install-file -Dfile=<path-to-file>\lib\jxbrowser-6.1.1.jar         -DgroupId=jxbrowser -DartifactId=jxbrowser-main    -Dversion=6.1.1 -Dpackaging=jar
    mvn install:install-file -Dfile=<path-to-file>\lib\jxbrowser-linux32-6.1.1.jar -DgroupId=jxbrowser -DartifactId=jxbrowser-linux32 -Dversion=6.1.1 -Dpackaging=jar
    mvn install:install-file -Dfile=<path-to-file>\lib\jxbrowser-linux64-6.1.1.jar -DgroupId=jxbrowser -DartifactId=jxbrowser-linux64 -Dversion=6.1.1 -Dpackaging=jar
    mvn install:install-file -Dfile=<path-to-file>\lib\jxbrowser-mac-6.1.1.jar     -DgroupId=jxbrowser -DartifactId=jxbrowser-mac     -Dversion=6.1.1 -Dpackaging=jar
    mvn install:install-file -Dfile=<path-to-file>\lib\jxbrowser-win-6.1.1.jar     -DgroupId=jxbrowser -DartifactId=jxbrowser-win     -Dversion=6.1.1 -Dpackaging=jar
    mvn install:install-file -Dfile=<path-to-file>\lib\license.jar                 -DgroupId=jxbrowser -DartifactId=license           -Dversion=6.1.1 -Dpackaging=jar

#####build the app with: 

    mvn compile
	
#####run the tests with: 

    mvn test

#####run the console application with:
    
    mvn -Dlogback.configurationFile=simplelogger.xml exec:java -Dexec.mainClass="com.sainsburys.webpage.WebPage" -Dexec.args="http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?listView=true&orderBy=FAVOURITES_FIRST&parent_category_rn=12518&top_category=12518&langId=44&beginIndex=0&pageSize=20&catalogId=10137&searchTerm=&categoryId=185749&listId=&storeId=10151&promotionId=#langId=44&storeId=10151&catalogId=10137&categoryId=185749&parent_category_rn=12518&top_category=12518&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true"  


