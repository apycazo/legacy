#!/bin/bash

# BotNet install/start

# Check parameters
if [ $# -eq 0 ]; then
	echo "Starting BotNet backend..."
	node server/app-be.js
else
	case $1 in
		"install")
			echo "install option"
			npm install
			break
		;;
		"debug")
			echo "Debuggin using node inspector"
			node-debug server/app-be.js
			#node --debug -p 5858 server/app-be.js
			break
		;;
		"reset")
			echo "Reseting db..."
			mongo --quiet botnet database/init.js
			echo "Done"
			break
		;;
	esac
fi
exit 0
