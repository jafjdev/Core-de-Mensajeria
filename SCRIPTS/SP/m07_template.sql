-- DROP FUNCTION m07_select_templates_by_campaign(integer);
CREATE OR REPLACE FUNCTION m07_select_templates_by_campaign(IN campaignId integer)
RETURNS TABLE (tem_id integer,tem_creation_date timestamp,tem_campaign_id integer,
		tem_application_id integer, tem_user_id integer, sta_id integer,
		sta_name varchar,ts_user_id integer) AS $gettemplates$
BEGIN
RETURN QUERY
select t.tem_id, t.tem_creation_date, t.tem_campaign_id, t.tem_application_id, t.tem_user_id, s.sta_id, s.sta_name, ts.ts_user_id
          from public.template t
          inner join public.template_status ts
          on ts.ts_template = t.tem_id
            inner join
            (
            select ts_template, max(ts_id) maxID from public.template_status
            group by ts_template
            )ts_ on ts_.ts_template = ts.ts_template
          and ts.ts_id = ts_.maxID
          inner join public.status s
          on ts.ts_status = s.sta_id
          inner join public.campaign c
          on c.cam_id = t.tem_campaign_id
          where c.cam_id = campaignId
          order by t.tem_id;
END;
$gettemplates$
LANGUAGE 'plpgsql' VOLATILE;

-- DROP FUNCTION m07_select_all_templates();
CREATE OR REPLACE FUNCTION m07_select_all_templates()
RETURNS TABLE (tem_id integer,tem_creation_date timestamp,sta_id integer,sta_name varchar) 
AS $$
BEGIN
RETURN QUERY
select t.tem_id, t.tem_creation_date, s.sta_id, s.sta_name
                    from template t
                    inner join template_status ts
                    on ts.ts_template = t.tem_id
                    inner join
                    (
                    select ts_template, max(ts_id) maxID from template_status 
                    group by ts_template
                    )ts_ on ts_.ts_template = ts.ts_template
                    and ts.ts_id = ts_.maxID
                    inner join status s
                    on ts.ts_status = s.sta_id
                    order by t.tem_id;
END;
$$
LANGUAGE 'plpgsql' VOLATILE;

-- DROP FUNCTION m07_select_privileges_by_user_company(integer,integer);
CREATE OR REPLACE FUNCTION m07_select_privileges_by_user_company(
	IN userId INTEGER, IN companyId INTEGER)
RETURNS TABLE (pri_id integer, pri_code varchar, pri_action varchar) AS $$
BEGIN
RETURN QUERY
	SELECT p.pri_id, p.pri_code, p.pri_action
	FROM public.responsability r
	INNER JOIN public.rol_pri rp
	ON r.res_rol_id = rp.rol_pri_rol_id
	INNER JOIN public.privilege p
	ON rp.rol_pri_pri_id = p.pri_id
	WHERE r.res_use_id = userId AND r.res_com_id = companyId
	AND SUBSTRING(p.pri_code from 2 for LENGTH(p.pri_code)) IS NOT DISTINCT FROM 'TEMPLATE';
	END;
$$
LANGUAGE 'plpgsql' VOLATILE;

-- DROP FUNCTION m07_select_campaign_by_user_company(integer,integer,integer);
CREATE OR REPLACE FUNCTION m07_select_campaign_by_user_company(IN one INTEGER, IN two INTEGER, IN three INTEGER)
RETURNS TABLE (cam_id integer, cam_name varchar, cam_description varchar,
		cam_status boolean, cam_start_date timestamp,cam_end_date timestamp,
		com_id integer, com_name varchar, com_description varchar,
		com_status boolean) AS $$
BEGIN
RETURN QUERY
SELECT c.cam_id, c.cam_name, c.cam_description, c.cam_status, c.cam_start_date, c.cam_end_date,  co.com_id, co.com_name, co.com_description, co.com_status
          FROM public.campaign c
          INNER JOIN public.responsability r
          ON c.cam_company_id = r.res_com_id
          INNER JOIN public.company co
          ON c.cam_company_id = co.com_id
          WHERE r.res_use_id = one OR (r.res_use_id = two AND r.res_com_id = three)
          ORDER BY c.cam_id;
	END;
        $$
        LANGUAGE 'plpgsql' VOLATILE;

-- DROP FUNCTION m07_select_channels_by_template(integer);
CREATE OR REPLACE FUNCTION m07_select_channels_by_template(IN templateId INTEGER)
RETURNS TABLE (tci_template_id INTEGER, ci_channel_id INTEGER, ci_integrator_id INTEGER,
		cha_name VARCHAR, cha_description VARCHAR) AS $$
BEGIN
RETURN QUERY
SELECT tci.tci_template_id, ci.ci_channel_id, ci.ci_integrator_id, 
       c.cha_name, c.cha_description
FROM channel_integrator ci
INNER JOIN template_channel_integrator tci
ON tci.tci_ci_id = ci.ci_id
 INNER JOIN channel c
 ON c.cha_id = ci.ci_channel_id
 WHERE tci.tci_template_id = templateId
 ORDER BY ci.ci_channel_id;
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;

 -- DROP FUNCTION m07_select_messages(integer);
 CREATE OR REPLACE FUNCTION m07_select_messages(IN templateId INTEGER)
 RETURN TABLE (mes_id INTEGER, mes_text VARCHAR) AS $$
 BEGIN
 RETURN QUERY
 SELECT * 
 FROM PUBLIC.MESSAGE 
 WHERE MES_TEMPLATE = templateId;
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;


 -- DROP FUNCTION m07_select_message(integer);
 CREATE OR REPLACE FUNCTION m07_select_message(IN templateId INTEGER)
 RETURN TABLE (mes_id INTEGER, mes_text VARCHAR) AS $$
 BEGIN
 RETURN QUERY
 SELECT mes_id,mes_text 
 FROM message 
 WHERE mes_template = templateId;
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;

 -- DROP FUNCTION m07_post_message(varchar, integer);
 CREATE OR REPLACE FUNCTION m07_post_message(INT message VARCHAR, INT templateId INTEGER) 
 RETURN TABLE (mes_id INTEGER) AS $$
 BEGIN
 INSERT INTO public.Message(mes_text,mes_template)
 VALUES (message, tem_id) returning mes_id;
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;

  -- DROP FUNCTION m07_post_message(varchar, integer);
 CREATE OR REPLACE FUNCTION m07_post_parameter_of_message(INT messageId INTEGER, INT companyId INTEGER, INT parameterName VARCHAR) 
 RETURN void AS $$
 BEGIN
 INSERT INTO public.message_parameter(mp_message,mp_parameter)
 VALUES (messageId, 
 (select par_id from public.parameter
 where par_company_id = companyId and par_name = parameterName));
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;

 CREATE OR REPLACE FUNCTION m07_update_message(INT message VARCHAR, INT templateId INTEGER)
 RETURN TABLE (mes_id INTEGER) AS $$
 BEGIN
 UPDATE public.message
 SET mes_text = message
 WHERE mes_template = templateId returning mes_id;
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;

 CREATE OR REPLACE FUNCTION m07_delete_parameter_of_message(INT messageId INTEGER)
 RETURN TABLE (mes_id INTEGER) AS $$
 BEGIN
 DELETE FROM public.message_parameter
 WHERE mp_message = messageIdl
 END;
 $$
 LANGUAGE 'plpgsql' VOLATILE;