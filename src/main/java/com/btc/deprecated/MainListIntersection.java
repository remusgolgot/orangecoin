package com.btc.deprecated;

import java.util.List;

public class MainListIntersection {

    public static void main(String[] args) {

        String s1 = "1DvMscSxNhaCoMmckF9FkP7jExPgtNDsMq\n" +
                "1FsnbrAKjmTCtEJ96TEwJTkrwjeQp2PUom\n" +
                "1GeaddWKTu9njyKmCno68yCV213HYwnMF2\n" +
                "1GX1yqxgkWbd3mvMunmyWZdLpgtMbwS6sA\n" +
                "1MSejqdwLeJDvLpHWrrXrfJL3Zo6m9jbNA\n" +
                "1AxEfRq6Dp2aiypdi2gNT2Q4vxbbnanPrk\n" +
                "186TugCjLeQNLUhqYaN6iqyyk8v3DAFZy4\n" +
                "1okLYWo6yBiCiJaxHMSC33a5cYejaMf75 \n" +
                "1Mk2Jxdq63DSdN7X6bCjr9rVKQckcNMrQ1\n" +
                "1FsxjkJFzGcLW6ypMjqNYShvBaKLAMF31h\n" +
                "1Kd8jdAYDLi3g1qgvvLqTeU9oMuwfwdy2i\n" +
                "12w5eVefnr1CyVKtbcLmM33niCfWrxqsue\n" +
                "1G3zKWBTMd9LL3Tm6V5XXadP1fvAwTE4kg\n" +
                "147ccqDgAqaZ4isJ3aAQBKDwAE39JCnW9R\n" +
                "1MzhUMiuqg1cqa4GuRiVdVUipjHhv89r2G\n" +
                "1P15SDx1hAZwqXBFhUSCocxUvs3YX2YcY3\n" +
                "1Gn7VXG8g6pQz11MvzmZWAYFxxRJ7uBFaP\n" +
                "15yZtTp4EhVQiEmPFonMAFEajH7duT9hrC\n" +
                "16xnHhmqKt29wd2KruqFAqpTmuoKUpqCHt\n" +
                "1GM4AqKAYCYXqtKywTC3Zz6rYgYTxYvbDC\n" +
                "19WSBCzQfm4yWtSRjhGnhGhdkkXWtGVuTQ\n" +
                "182TDnXn4VhYRmq1HiPyMKv2hYCPjHuMiB\n" +
                "1EPAosQ5pRBCPxionfYXPb6rhxf5rMFwez\n" +
                "1NuNa4sXyzfQ8fEaMHPym93G8UKEZGc7Pd";

        String s2 = "186tV3WUXJBxvRTH7w8SCDk4qxGcAmRNnK\n" +
                "12XGWXzYmqbw7R81aXzzrxd8kU2Rixy6hp\n" +
                "1GquniFprXVGm5zN13VMa9reyGsnpjQh52\n" +
                "169nqBPnVaBVpfNBF412qitFWki9SqvXc6\n" +
                "19umGdotR99xPZUqJZ6FH5QAxpuBWL2qnP\n" +
                "1Mv5U7ET42fFbz8oe6xrqwrVfM9RcVLJf3\n" +
                "15VaaLskYYjnKYBjxpjSABX2quyvUEBvnf\n" +
                "16V6Emoj1w2uHVnx58urHk7Sr2w4wH1GGG\n" +
                "1Mx6yrhhWkfWB13o2w6VoFG5zJXByxSXMY\n" +
                "1wwya72pvScYtRHiaMbSh4s4M6kVZKMwv\n" +
                "1DuEzMQV7VdpXjjNKRm8SJMt7Vq9GchcLA\n" +
                "1MFHzUCHycVy6ffbWnzu7SwD9acdSU6DUL\n" +
                "1Bf7mvkHE6VKorYZU9YNYuff9vWGqpHoQi\n" +
                "1CxfDhY3rjbL6wGKhLNc8XS9nvUZbSwSgK\n" +
                "1GQ7mesAGnR9Qjm8MsKZh5xgyessCnN7q6\n" +
                "1CFPUZJC7tNJgYnJDQALS7ac2ckThSuDNR\n" +
                "12x73pngAyXVvCfLQEXSqRuXNG5MGQt9QF\n" +
                "1EpJCATjjfSb9sroPiv5R27b4oiLwHSM1T\n" +
                "1JXJfMC2QP6c8apXtYXKyDeAAHnQZ5Hrir\n" +
                "12WUx3ULJCcD9Zf63djjoFBzrQEL6xX5sL\n" +
                "18p8FKEuhyYPjB2orPv3mzhtXPqeYuE9Ub\n" +
                "1P3nFZU1WmCM9sBakiptj7qKHr94M2cC7w\n" +
                "1D1HjYwm9omR8f1u5bgFZozTGScNJYBXwV\n" +
                "18tFcweZGcv8GPBbpcqjM8xudSBjizbttp\n" +
                "1B11PCsQYfz2TyLvdKUN9vC5bdzYmdnAzB\n" +
                "1MBWVtrmvKhzVZtfz1tnEDsqaVdVJqvCNa\n" +
                "1572P6uMNHBfffsJboVCwXJP1AqLvn3jWw\n" +
                "1Js6QUodhkUPcpS5npuFVVtH6KWk8Bzus2\n" +
                "1JZCgoj3rvuKpwNFyptR3itaZxo59pwooX\n" +
                "1D2GkjZ9D5KqqeeuuiGJXqbc9CeSWEvpuS\n" +
                "1Nt4By3vNwon6imF19BDvrf984dLJMttR6\n" +
                "14pGZ8GNEaNi9HM791QMBycTg8oYGAYgbn\n" +
                "19iNkguED79B9A9G2vjfMikMsaY15B45wr\n" +
                "15VnvDPQXYJqzVGGL9z1ehHs9emSFRJbrq\n" +
                "1NSwk7GDugQC6LsbPbGtwoYrrGkSraQe8a\n" +
                "1MjAymXtK2w6RsXoExdtN8thi36SbxHRGa\n" +
                "16vtEfTy3LsP5KuUbToG526TpFXozZDP4Y\n" +
                "19t4PNudzJ6Pggqdofm658E9nKGnabXDMv\n" +
                "1NsfX5hCjoXswE1GrdwXEzFmJPeqKnJcqM\n" +
                "14oWXnq9cHYEZc3dzQu2bkEHYf6EMtN7uM\n" +
                "1GnaXV148QJWbT1QuYpuNRF3UkPikQZnEZ\n" +
                "1PJCMGHQVnqz9b1eHZ84EEnoYrjQC4cBai\n" +
                "1DjwRCjT18UTCMzJZ6GK9PeXwP88smrt7n\n" +
                "12jtBrd15N3xof2bNSCLJneMP4uricbcaf\n" +
                "1G3BhXmhfr6i85EMV21Ez9ruoTdF9GYK4F\n" +
                "1H4XHsXwdGgWMT65PTyGeVXrSHiUvUCWyH\n" +
                "1GcnQ1J35ZXab35cSyVjuvVpm3QtjDEYTs\n" +
                "1H822SZMV6upNyaLyYtb7tXA32wUc17k3e\n" +
                "15fyz1XFeSh4Av95ZYEU8eDnYLFDXKyrJF\n" +
                "19XcYPujxrxMtUJnxU8JvKNxvogSDJ5th3\n" +
                "1567rR5v7m4wrLuczwHXHPui7LbAPQwmGP\n" +
                "1G4iWhWp4Hu9JD8BQSprZDGnnDpLso1vfD\n" +
                "1H9iAvQWsmQTBxcA3NZESEDBaDVTrCQ99X\n" +
                "1A7tSRzVZ56St8iyGSnSMV4ooDQEyZ9bRV\n" +
                "1PhAmSXQuQsm5c69w1arFM2sN4BhZHbSaT\n" +
                "1FLxCK9tLYy2d5Gf9AfgZTu3WLyKAHHx7v\n" +
                "185asKuMvceywAWwY92uwyfghkZXvXH5t9\n" +
                "1Fx9WBMHRBXTQhUUS3cuuCQWJbLfkzKg4U\n" +
                "1JSHar7SVMkwamt1Ry1z9RX2nm9MkV7kFH\n" +
                "1G3KsPvx6WpBttrgTQ1f2hWL69376DNzvo\n" +
                "1H5Ztqir4iYw3pXL95k3LsA76CbTq4VdPJ\n" +
                "1QBvbLsRwb1HmtwTG1WGMN4Z8xd3mA83fX\n" +
                "1G26XYDZcY4j4GJ7bo9btBwCa74r9tBQyM\n" +
                "1B17qtmzevrwXHMV4JkMfxLLLufa3GtZaj\n" +
                "156ezyfJEbyjb1mNZDRcdMDUxZ9f8tv6iv\n" +
                "14ZT7wtrwjAVGGAB6E8tdHg63q1GaTdCSY";

        List<String> list1 = List.of(s1.split("\n"));
        List<String> list2 = List.of(s2.split("\n"));

        for (String s : list1) {
            for (String value : list2) {
                if (s.equals(value)) {
                    System.out.println(s);
                }
            }
        }
    }
}
