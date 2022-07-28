cd webapp/cake-explorer
call npm install
call ng build
cd ../../
call mvn package
call mvn exec:java