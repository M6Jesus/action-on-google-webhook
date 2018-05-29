
CREATE SEQUENCE public.destination_id_seq;
CREATE TABLE public.destination (
                destinationId INTEGER NOT NULL DEFAULT nextval('public.destination_id_seq'),
                url VARCHAR(100) NOT NULL,
				enLigne BOOLEAN NOT NULL,
                CONSTRAINT destination_pk PRIMARY KEY (destinationId)
);
ALTER SEQUENCE public.destination_id_seq OWNED BY public.destination.destinationId;


CREATE SEQUENCE public.message_id_seq;
CREATE TABLE public.message (
                messageId INTEGER NOT NULL DEFAULT nextval('public.message_id_seq'),
                messageBody VARCHAR(100) NOT NULL,
				contentType VARCHAR(100) NOT NULL,
				destination_id INTEGER NOT NULL,
				heure TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,	
                CONSTRAINT message_pk PRIMARY KEY (messageId)
);
ALTER SEQUENCE public.message_id_seq OWNED BY public.message.messageId;
ALTER TABLE public.message ADD CONSTRAINT destination_message_fk
FOREIGN KEY (destination_id)
REFERENCES public.destination (destinationId)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;