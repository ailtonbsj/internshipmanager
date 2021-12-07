#!/bin/bash
/usr/bin/pg_ctlcluster 14 main start &
/etc/service/xvfb/run &
/etc/service/webswing/run
