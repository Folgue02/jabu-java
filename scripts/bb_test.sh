#!/bin/bash
PLAYGROUND_DIR="playground"

function die() {
    echo $1
    exit $2
}

function cmd() {
    echo "CMD: $@"
    $@

    if [ $? -ne 0 ]; then
        die "Command '$@' has failed." 2
    fi
}

function installJabu() {
    cmd ./helper.sh
}

function create() {
    # Deps
    installJabu

    
    cmd jabu create -n $PLAYGROUND_DIR -t binary
    cmd tree $PLAYGROUND_DIR
}

function build() {
    # Deps
    create

    cmd cd $PLAYGROUND_DIR
    cmd jabu build 
}

function clean() {
    # No deps
    
    if [ -d $PLAYGROUND_DIR ]; then
        echo "Removing '$PLAYGROUND_DIR'"
        cmd rm -rf $PLAYGROUND_DIR
    else
        echo "'$PLAYGROUND_DIR' doesn't exist."
    fi
}

if [ -z $1 ]; then
    die "No action provided" 1
else
    case $1 in
        "create")
            create
            ;;
        "install")
            installJabu
            ;;
        "clean")
            clean
            ;;
        "build")
            build
            ;;
        *)
            die "Not recognized: $1" 1
    esac
fi
