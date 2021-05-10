package com.adminpro.auth;

public class JwtConfig {


    public static final  String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAorEXcVRhpRwNlVmEEgbt1rdp2TeSB2eJu/7xtqqsEAFWgwNq\n" +
            "88glfXnUZZaxSnAIatr5cXwVuW2oLWHBepq7tDkHpALLBguVcCSnlehymkqx16E3\n" +
            "mMViGB9xXfWOZjAFW11epAiT9hg8TUb8qqAkan7xEpp38rXrPyol3ybGLL/NbBHu\n" +
            "ZUBx2La4x1F8wD0vsum4O3hK2CGU8kpLh+cOpL73zvCeUxglH7UwUXkL3Qrc5Gv1\n" +
            "TvqRCFllm6tIb6Uw+ltG6Mmn3VVz4a7u8+oCdJnP7MxMXykAx4PtuGWzTvkGqyLx\n" +
            "ecxKqt0VFuO8VFHIqZ1VE8vlc3PLSCGSlu4lHQIDAQABAoIBAH/FcVHSIXXYmFpp\n" +
            "GS5HPtdY6MptuEQUbrm0ugTp+YQAaP8v2flngPoQilzWgpqhbhrtquYTu8QLeP9b\n" +
            "POabSzt5ZqTKhMRUqKKQRwWWrxq5+v4MYWub6TRkzVYrmhBrOmnM5J0TF5E463MN\n" +
            "gzZF2xPAanhf1EBL21uaMHAXNaDCKXBPhOqUEeWFKC6Etf0dblIBPFtAjLS5QrBx\n" +
            "ePmVSFKyYdzwD0D+gnzyDaRTtJQy4BgxXuQfZSZn9umxBZwNhLyE0kvvsUuuSChN\n" +
            "YbhmV/ZVJv3xfmCkCXXurlDWF87Z8x3NS8Tgo0mP9R0n0kRjngDKVOh6s8LtTEEU\n" +
            "2+jHSoECgYEAzn/Sfjia4PznNqOENRF5qH7nQj0hs0ZfVoO/i3aCv9uI1h7X5SI/\n" +
            "vMBMMWzIkC6S+erRdPxST1CeJfHl2sJTs0BGyFZpBuB1zdiwrhoHX4cq2uJNRfDk\n" +
            "jt6+Kruvx75BufGch+9cWP03t+CBJdgXYUOTv/qjtS291nUJGPxL000CgYEAybDy\n" +
            "HB3/wgLwYGOzUHyzYEqLAk+lG7TIwy7oIXgRrxaXAgc6PFgKMp5vYQs4LtjgnOYy\n" +
            "0pTlgeFdxmeCzaK39PKvC5qxk2maNKFXkntS2lT4yy+tfQ30BMFKPebBHi8DMI3z\n" +
            "6DEn8+92cC1HSOIpQyhcascDGlmKeEJz6vfRERECgYEAof/ZTLhvmM4PPeguEcvx\n" +
            "22QjXO3EgHZW2PJm1KxWitlZklGEIfgqdJOPsr6a4bIIEBRN2i8RC7Q9PZt2LZaC\n" +
            "P3/Ph99yabix8plqkktNOX46+pjK8nG3fPX1zmSL1HHH8m4/1MzNb+rHSXPRHbVX\n" +
            "MjDnupaQ09Rct9/9ESLtAYkCgYEAklINgzp8RosNSlBqUVT2aUALExUmyi/gikFo\n" +
            "ZIeQBL7VtMH7jLWuSETKttOF4e6zHQCgFEezLQOLnZmQupB29nxtY3TJqREur0nx\n" +
            "lzJizERi7j1blmLeCekUbE4RAk3G+Z2yOlwXCRBLk1w13/k7SB5q1YglhyM4osBL\n" +
            "CkbY5JECgYAld/v9gPQFmO9CoR+uoEBFwWIpnlQ9pRrBSoqa4s0i3ljHqP66y9l7\n" +
            "tkiCkoxVp1S5xLJEzfCIPNPASO8mbIu34jzKJpxMAM4Fd78SB2YN/MCbOduqS8ut\n" +
            "x9OvDPp814F/wbvlRl9fAcIw13I4DGpJhXCFdPvPPJofaU9uG7v+Ag==\n" +
            "-----END RSA PRIVATE KEY-----";

    public static final  String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAorEXcVRhpRwNlVmEEgbt\n" +
            "1rdp2TeSB2eJu/7xtqqsEAFWgwNq88glfXnUZZaxSnAIatr5cXwVuW2oLWHBepq7\n" +
            "tDkHpALLBguVcCSnlehymkqx16E3mMViGB9xXfWOZjAFW11epAiT9hg8TUb8qqAk\n" +
            "an7xEpp38rXrPyol3ybGLL/NbBHuZUBx2La4x1F8wD0vsum4O3hK2CGU8kpLh+cO\n" +
            "pL73zvCeUxglH7UwUXkL3Qrc5Gv1TvqRCFllm6tIb6Uw+ltG6Mmn3VVz4a7u8+oC\n" +
            "dJnP7MxMXykAx4PtuGWzTvkGqyLxecxKqt0VFuO8VFHIqZ1VE8vlc3PLSCGSlu4l\n" +
            "HQIDAQAB\n" +
            "-----END PUBLIC KEY-----";
}
