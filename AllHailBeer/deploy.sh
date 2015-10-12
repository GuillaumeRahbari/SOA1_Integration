#!/bin/bash
mvn clean package
cp target/*.jar $1

exit 0