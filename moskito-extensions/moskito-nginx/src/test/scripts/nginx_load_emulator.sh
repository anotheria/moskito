#!/usr/bin/env bash

#emulate some random load on nginx by getting it's status page
#consider using it next way:
# nohup ./nginx_load_emulator.sh >> nginx_load_emulator.log &

#nginx status page url. Can be changed to any other url handled by nginx
url="http://localhost:80/nginx_status"
autostop=true
repeats=60
delay=60s

log=nginx_load_emulator.log
pidfile=nginx_load_emulator.PID

if [[ $1 = "stop" ]]; then
    if [[ -e ${pidfile} ]]; then
        pid=`cat ${pidfile}`
        cmdline=/proc/${pid}/cmdline
        if [[ -e ${cmdline} ]]; then
            grep -q "nginx_load_emulator" ${cmdline} &&
            kill -9 ${pid} &&
            rm ${pidfile} &&
            echo -e "\n\n"`date` : STOPPED LOADING NGINX 2>&1 | tee -a ${log} &&
            echo "nginx_load_emulator process killed." 2>&1 | tee -a ${log}
        else
            echo "Could not find nginx_load_emulator process, maybe it's not running?"
        fi
    else
        echo "PID file does not exist, maybe load emulator is not running?"
    fi
    exit
fi

echo `date` : "STARTED LOADING NGINX" > ${log}
echo `date` : "PARAMS: autostop=${autostop} repeats=${repeats} delay=${delay} url=${url}" >> ${log}
if [[ ${autostop} = false ]]; then
    echo -e `date` : "DO NOT FORGET TO STOP THIS SCRIPT\n\n" >> ${log}
fi

echo $$ > ${pidfile}

while [[ ${autostop} = true && ${repeats} -gt 0 || ${autostop} = false ]]; do
        random=`shuf -i 1-5 -n 1`
        iterations=$((2000/$random))

        echo `date` : executing ${iterations} requests with ${delay} delay after last >> ${log}
        while [[ ${iterations} -gt 0 ]]; do
            curl -s ${url} >> /dev/null
            iterations=$((iterations - 1))
        done

        if [[ ${autostop} = true ]]; then
            let $((repeats--))
        fi

        sleep ${delay}
done

rm ${pidfile}
echo -e "\n\n"`date` : STOPPED LOADING NGINX >> ${log}
