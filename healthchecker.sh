#!/bin/bash
wget -O /dev/null http://127.0.0.1:9500/health && exit 0 || exit 1