BEGIN TRANSACTION;


--- ================================================================================
--- destination
--- ================================================================================
	INSERT INTO public.destination (destinationId, url, enLigne) VALUES (	1,	'http://c92486aa.ngrok.io/destination', TRUE);
	INSERT INTO public.destination (destinationId, url, enLigne) VALUES (	2,	'http://c92486aa.ngrok.io/destination', TRUE);
	INSERT INTO public.destination (destinationId, url, enLigne) VALUES (	3,	'http://c92486aa.ngrok.io/destination', TRUE);
	SELECT setval('public.destination_id_seq', 10, true);

--- ================================================================================
--- message
--- ================================================================================
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	1,	'premier message test',	'application/json', 1);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	2,	'deuxieme message test', 'application/json', 1);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	3,	'troisieme message test', 'application/json', 1);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	4,	'quatrieme message test', 'application/json', 2);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	5,	'ouuuu message test', 'application/json', 1);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	6,	'uuuuu message test', 'application/json', 3);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	7,	'jjjj message test', 'application/json', 2);
	INSERT INTO public.message (messageId, messageBody, contentType, destination_id) VALUES (	8,	'quatrioooeme message test', 'application/json', 1);
	SELECT setval('public.message_id_seq', 1000, true);
	
	
COMMIT;
	
