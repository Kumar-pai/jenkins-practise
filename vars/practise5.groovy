// vars/practise-5.groovy

def call(String executor) {
    script {
        if (executor == 'Practise') {
            echo "Hi Kumar"
        } else if (executor == "Jenkins") {
            echo "Hi Jenkins"
        } else {
            echo "unrecognizable"
        }
    }
}