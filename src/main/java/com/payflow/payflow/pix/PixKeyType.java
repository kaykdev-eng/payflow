package com.payflow.payflow.pix;

import java.util.UUID;

public enum PixKeyType {
    CPF {
        @Override
        public String normalize(String value) {
            String cpfValid = requireText(value, "CPF");
            String cpfClean = cpfValid.replaceAll("\\D", "");
            String regexCpf = "\\d{11}";
            if (!cpfClean.matches(regexCpf)) {
                throw new InvalidPixKeyException("Invalid CPF. Check the numbers given");
            }

            return cpfClean;
        }
    },
    CNPJ {
        @Override
        public String normalize(String value) {
            String cnpjValid = requireText(value, "CNPJ");
            String cnpjClean = cnpjValid.replaceAll("[\\s./-]", "").toUpperCase();
            String regexCnpj = "[A-Z0-9]{12}\\d{2}";

            if (!cnpjClean.matches(regexCnpj)) {
                throw new InvalidPixKeyException("Invalid CNPJ format");
            }

            return cnpjClean;
        }
    },
    EMAIL {
        @Override
        public String normalize(String value) {
            requireText(value, "EMAIL");
            String emailClean = value.trim().toLowerCase();
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            if (!emailClean.matches(emailRegex)) {
                throw new InvalidPixKeyException("Invalid Email. Check the email field");
            }

            return emailClean;
        }
    },
    PHONE {
        @Override
        public String normalize(String value) {
            requireText(value, "PHONE");
            String brazilianRegexPhone = "55[1-9]{2}9\\d{8}";
            String phoneClean = value.replaceAll("\\D", "");
            String phone = phoneClean.length() == 11 ? "55" + phoneClean : phoneClean;

            if (!phone.matches(brazilianRegexPhone)) {
                throw new InvalidPixKeyException("Invalid Phone. Check the Phone field");
            }

            return "+" + phone;
        }
    },
    RANDOM {
        @Override
        public String normalize(String value) {
            requireText(value, "RANDOM");
            String randomClean = value.trim().toLowerCase();
            try {
                if (!UUID.fromString(randomClean).toString().equals(randomClean)) {
                    throw new InvalidPixKeyException("Invalid random key");
                }
            } catch (IllegalArgumentException e) {
                throw new InvalidPixKeyException("Invalid random key");
            }


            return randomClean;
        }
    };

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidPixKeyException(fieldName + " is required");
        }
        return value;
    }

    public abstract String normalize(String value);
}
