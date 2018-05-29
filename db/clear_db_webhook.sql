BEGIN TRANSACTION;

--- ===== Purge des tables
DELETE FROM public.destination;
DELETE FROM public.message;



--- ===== Réinitialisation des séquences
SELECT setval('public.niveau_bug_id_seq', 1, false);
SELECT setval('public.destination_id_seq', 1, false);



COMMIT;