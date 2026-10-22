ALTER TABLE wallets DROP CONSTRAINT chk_balance;
ALTER TABLE transfers ADD CONSTRAINT cnk_amount CHECK ( amount > 0 )