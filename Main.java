package org.example;

//importing web3j packages and installing it.
import org.web3j.protocol. Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public class Main {
            public static void main(String[] args) throws Exception {
              //Using a mainet of ethereum from infura.io to get the balance details of the enetered address
                final Web3j client = Web3j.build(
                        new HttpService (
                                "https://mainnet.infura.io/v3/24d69b33b9964b2ba714595c30ccf212")//mainnet link
                );
                final String ethAddress = "0xf40239A9AD770D6983B73b5c43b30669A56A8a6E";//Can use any address.
                final EthGetBalance balanceResponse = client    //to get balace response.
                .ethGetBalance(ethAddress, DefaultBlockParameter.valueOf("latest")).sendAsync().get(10, TimeUnit.SECONDS);
                final BigInteger unscaledBalance = balanceResponse.getBalance();
                final BigDecimal scaledBalance = new BigDecimal(unscaledBalance).divide(new BigDecimal(1000000000000000000L),18, RoundingMode.HALF_UP);
                System.out.println("unscaledBalance" + unscaledBalance);//unscaled balance is too big, which is then converted to actual balance by dividing it.
                System.out.println("scaledBalance :" + scaledBalance);
    }
}
