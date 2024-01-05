#!/bin/bash

TARGET_FILE="$(pwd)/app/build/install/app/bin/app"
TARGET_LINK="/bin/jabu"

function installJabu() {
    gradle installDist
    if [ -e $TARGET_LINK ]; then
        echo "Removing old link..."
        sudo rm -rf $TARGET_LINK
    fi
    echo "Creating link..."
    sudo ln -s $TARGET_FILE $TARGET_LINK
}

installJabu

