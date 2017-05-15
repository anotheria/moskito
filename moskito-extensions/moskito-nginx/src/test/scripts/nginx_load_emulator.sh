#!/usr/bin/env bash

#emulate some random load on nginx by getting it's status page

#nginx status page url. Can be changed to any other url handled by nginx
url="http://localhost:80/nginx_status"
autostop=true
repeats=60
delay=60s
scriptName=`basename "$0"`
scriptName=${scriptName%.sh}

log=${scriptName}.log
pidfile=${scriptName}.PID

shopt -s expand_aliases

alias echo='echo -e '
alias echod='echo -e `date` : '

if [[ $1 = "stop" ]]; then
    if [[ -e ${pidfile} ]]; then
        pid=`cat "${pidfile}"`
        ps uq ${pid} | grep -q "${scriptName}"
        if [[ $? -eq 0 ]]; then
            kill -9 ${pid} &&
            rm ${pidfile} &&
            echod STOPPED LOADING NGINX 2>&1 | tee -a ${log} &&
            echod "${scriptName} process killed." 2>&1 | tee -a ${log}
            exit 0
        else
            echo "Could not find ${scriptName} process, maybe it's not running?"
        fi
    else
        echo "PID file does not exist, maybe load emulator is not running?"
    fi
    exit 1

elif [[ $1 = "start" ]]; then

    echod "STARTED LOADING NGINX"  2>&1 | tee -a ${log}
    echod "PARAMS: autostop=${autostop} repeats=${repeats} delay=${delay} url=${url}"  2>&1 | tee -a ${log}
    if [[ ${autostop} = false ]]; then
        echod "DO NOT FORGET TO STOP THIS SCRIPT\n\n"  2>&1 | tee -a ${log}
    fi

    echo $$ > ${pidfile}

    while [[ ${autostop} = true && ${repeats} -gt 0 || ${autostop} = false ]]; do
            random=`shuf -i 1-5 -n 1`
            iterations=$((2000/$random))

            echod executing ${iterations} requests with ${delay} delay after last  2>&1 | tee -a ${log}
            while [[ ${iterations} -gt 0 ]]; do
                curl -s ${url} >> /dev/null
                iterations=$((iterations - 1))
            done

            if [[ ${autostop} = true ]]; then
                let $((repeats--))
            fi

            sleep ${delay}
    done

    echod : STOPPED LOADING NGINX  2>&1 | tee -a ${log}
    rm ${pidfile}
    exit 0

else
    echo "===================================================================="
    echo "Usage: ${scriptName}.sh COMMAND"
    echo "COMMAND is 'start' or 'stop'"
    echo "\nscript is writing its log to ${log}, check if needed."
    echo "Also recommended way of starting it is through nohup:"
    echo " nohup bash ${scriptName}.sh start >> /dev/null &"
    echo "Stopping:\n bash ${scriptName}.sh stop"
    echo "===================================================================="

    exit 1
fi