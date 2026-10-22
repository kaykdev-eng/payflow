ALTER TABLE users DROP COLUMN balance;
ALTER TABLE wallets ADD CONSTRAINT chk_balance CHECK ( balance > 0 );