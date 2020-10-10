# AP-Tool Installation Script
# © 2020 Mukul Agarwal

UTILS_DIR="$HOME/.utils"
JARS_DIR="$UTILS_DIR/jars"
VERSION="v0.1.0-alpha"

 write_to_profile() (
    echo $SHELL | grep bash >> /dev/null \
    && echo "Adding ~/utils to ~/.bashrc and ~/.bash_profile" \
    && echo 'export PATH=$PATH:'"$UTILS_DIR" | tee -a $HOME/.bash_profile >> $HOME/.bashrc \
    && return


    echo $SHELL | grep zsh >> /dev/null \
    && echo "Adding ~/utils to ~/.zshrc" \
    && echo 'export PATH=$PATH:'"$UTILS_DIR" >> $HOME/.zshrc \
    && return
    
    echo "Adding ~/utils to ~/.profile as zsh or bash are not default shells" \
    && echo 'export PATH=$PATH:'"$UTILS_DIR" >> $HOME/.profile \
    && return
)

function create_directories() (
    if [ -d $UTILS_DIR ]; then
        echo "~/.utils directory exists."
    else
        echo "~/utils directory does not exist -- creating..."
        mkdir $UTILS_DIR
    fi

    if [ -d $JARS_DIR ]; then
        echo "~/.utils/jars"
    else
        echo "~/utils/jars directory does not exist -- creating..."
        mkdir $JARS_DIR
    fi
)

#Note: not symlinks
create_links() (
    echo "Creating 'ap' file in $UTILS_DIR"
    rm -f $UTILS_DIR/ap
    echo -e '#!'"/bin/bash\n\njava -jar $JARS_DIR/ap.jar "'$@' > $UTILS_DIR/ap

    echo -n "Modifying permissions..."
    chmod +x $UTILS_DIR/ap
    echo -e "\rModified permissions    "
)

download() (
    FETCH_PATH="https://github.com/agarmu/AP-Tool/releases/download/$VERSION/ap.jar"
    curl -fsL $FETCH_PATH --output $JARS_DIR/ap.jar
)

create_directories
download
create_links
write_to_profile