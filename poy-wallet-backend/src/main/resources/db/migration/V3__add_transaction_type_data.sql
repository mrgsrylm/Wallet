INSERT INTO public."transaction_type" (id, "name", description) VALUES (1, 'Transfer', 'Transfer type');
INSERT INTO public."transaction_type" (id, "name", description) VALUES (2, 'Payment', 'Payment type');
INSERT INTO public."transaction_type" (id, "name", description) VALUES (3, 'Shopping', 'Shopping type');

SELECT setval('sequence_transaction_type', max(id)) FROM public."transaction_type";