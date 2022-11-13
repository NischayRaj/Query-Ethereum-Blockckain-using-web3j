
//Code is incomplete, working on it


const Web3 = require('web3');

class transactionCheck {
    web3;
    web3ws;
    account;
    subscription;

    constructor(projectId, account) {
        this.web3ws = new Web3(new Web3.providers.WebsocketProvider('wss://goerli.infura.io/ws/v3/33a32da698c645fcb9d67d4bd66f7745' + projectId));
        this.web3 = new Web3(new Web3.providers.HttpProvider('https://goerli.infura.io/v3/33a32da698c645fcb9d67d4bd66f7745' + projectId));
        this.account = account.toLowerCase();
    }

    subscribe(topic) {
        this.subscription = this.web3ws.eth.subscribe(topic, (err, res) => {
            if (err) console.error(err);
        });
    }

    watchTransactions() {
        console.log('Watching all pending transactions...');
        this.subscription.on('data', (txHash) => {
            setTimeout(async () => {
                try {
                    let tx = await this.web3.eth.getTransaction(txHash);
                    if (tx != null) {
                        if (this.account == tx.to.toLowerCase()) {
                            console.log({address: tx.from, value: this.web3.utils.fromWei(tx.value, 'ether'), timestamp: new Date()});
                        }
                    }
                } catch (err) {
                    console.error(err);
                }
            }, 60000)
        });
    }
}

let txChecker = new transactionCheck(process.env.INFURA_ID, '033a32da698c645fcb9d67d4bd66f7745');
txChecker.subscribe('pendingTransactions');
txChecker.watchTransactions();


