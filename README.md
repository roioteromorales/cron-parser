# Cron Expression Parser 

## How to run:
Just run the springboot java application using your IDE run configuration with the argument as a string. eg: `"*/15 0 1,15 * 1-5 /usr/bin/find"`

You can run it with gradle as well with the wrapper running the commandline command:

`./gradlew run --args="\"*/15 0 1,15 * 1-5 /usr/bin/find\""`

You could as well just compile and run as normal java app via terminal, passing the argument at invoke time.

## About tests:

Only the most important tests were implemented just to show the style of testing and avoid using too much time in repetitive easy test. 

In a normal work environment every part of the code that have some logic should be tested.