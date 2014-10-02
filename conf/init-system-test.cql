-- sudo mkdir -p /data/cassandra/{c1,c2,c3}/data
-- sudo mkdir -p /data/cassandra/{c1,c2,c3}/commitlog
-- sudo chmod 777 /data/cassandra/{c1,c2,c3}/data
-- sudo chmod 777 /data/cassandra/{c1,c2,c3}/commitlog
-- docker run -d --name c1 -v /data/cassandra/c1/data:/var/lib/cassandra/data -v /data/cassandra/c1/commitlog:/var/lib/cassandra/commitlog poklet/cassandra
-- docker run -d --name c2 -v /data/cassandra/c2/data:/var/lib/cassandra/data -v /data/cassandra/c2/commitlog:/var/lib/cassandra/commitlog poklet/cassandra start $(./scripts/ipof.sh c1)
-- docker run -d --name c3 -v /data/cassandra/c3/data:/var/lib/cassandra/data -v /data/cassandra/c3/commitlog:/var/lib/cassandra/commitlog poklet/cassandra start $(./scripts/ipof.sh c1)
-- docker run -it --rm --link c1:c1 -v /data/cassandra/scripts:/data poklet/cassandra cqlsh c1
-- docker run -it --rm --link c2:c2 -v /data/cassandra/scripts:/data poklet/cassandra cqlsh c2
-- docker run -it --rm --link c3:c3 -v /data/cassandra/scripts:/data poklet/cassandra cqlsh c3
-- docker run -it --rm --link c1:c1 poklet/cassandra nodetool 
-- docker run -d -p 8888:8888 --link c1:c1 -name opscenter poklet/opscenter
-- docker run -it --rm --link c1:c1 poklet/cassandra nodetool removenode -h 172.17.0.4 72f2fbfa-7607-4eeb-bd88-1ec5e2a4da31
-- docker run -it --rm --link c1:c1 poklet/cassandra nodetool flush -h 172.17.0.4 dotmarks users dotmarks dotmarksbyuser
-- docker run -it --rm --link c1:c1 poklet/cassandra nodetool status -h 172.17.0.4 


CREATE KEYSPACE dotmarks WITH REPLICATION =
 {'class': 'SimpleStrategy', 'replication_factor': 1};


USE dotmarks;


CREATE TABLE test_table (
  id text,
  test_value text,
  PRIMARY KEY (id)
 );


INSERT INTO test_table (id, test_value) VALUES ('1', 'one');

INSERT INTO test_table (id, test_value) VALUES ('2', 'two');

INSERT INTO test_table (id, test_value) VALUES ('3', 'three');

SELECT * FROM test_table;


CREATE TABLE users(
  user_id uuid,
  username text,
  password text,
  email text,
  active boolean,
  created timestamp,
  updated timestamp,
  PRIMARY KEY(username, user_id)
);

INSERT INTO users (user_id, username, password, email, active, created, updated) VALUES(blobAsUuid(timeuuidAsBlob(now())), 'ivan', 'ivan', 'ivan@pedrazas.me', true,dateof(now()), dateof(now()));

INSERT INTO users (user_id, username, password, email, active, created, updated) VALUES(blobAsUuid(timeuuidAsBlob(now())), 'marco', 'marco', 'marco@polo.com', true,dateof(now()), dateof(now()));

CREATE TABLE dotmarks(
    user uuid,
    url text,
    title text,
    tags list<text>,
    source text,
    star boolean,
    created timestamp,
    updated timestamp,
    PRIMARY KEY(url, user)
);

CREATE TABLE dotmarksByUser(
    user uuid,
    url text,
    title text,
    tags list<text>,
    source text,
    star boolean,
    created timestamp,
    updated timestamp,
    PRIMARY KEY(user, url)
);

-- SELECT columnfamily_name FROM System.schema_columnfamilies WHERE keyspace_name='dotmarks';


UPDATE dotmarks
  SET
      title = 'Google',
      tags = ['search', 'engine'],
      source = 'cli',
      star = false,
      created = dateof(now()),
      updated = dateof(now())
  WHERE url = 'http://google.com' and user = 56e69f20-3f47-11e4-b6bf-fdcc844fb3b0;

UPDATE dotmarksByUser
  SET
      title = 'Google',
      tags = ['search', 'engine'],
      source = 'cli',
      star = false,
      created = dateof(now()),
      updated = dateof(now())
  WHERE url = 'http://google.com' and user = 56e69f20-3f47-11e4-b6bf-fdcc844fb3b0;


UPDATE dotmarks
  SET
      title = 'Google',
      tags = ['search', 'engine'],
      source = 'cli',
      star = false,
      created = dateof(now()),
      updated = dateof(now())
  WHERE url = 'http://google.com' and user = ec4481d0-3f25-11e4-846a-091830ac5256;

UPDATE dotmarksByUser
  SET
      title = 'Google',
      tags = ['search', 'engine'],
      source = 'cli',
      star = false,
      created = dateof(now()),
      updated = dateof(now())
  WHERE url = 'http://google.com' and user = ec4481d0-3f25-11e4-846a-091830ac5256;


-- INSERT INTO dotmarks (user , url, title, tags, source, star, created, updated) VALUES (ec4481d0-3f25-11e4-846a-091830ac5256,'https://api.dotmarks.net', 'dotMarks', ['test', 'links', 'api'], 'system test', false,  dateof(now()),  dateof(now()));


-- curl -d '{"url": "http://api.mongodb.org/python/current/tutorial.html","title": "MongoDB Tutorial", "user": "ec4481d0-3f25-11e4-846a-091830ac5256", "tags": ["python", "test"]}' -H 'Content-Type: application/json' http://localhost:8080/dotmarks



-- curl -d '{"url": "https://developers.google.com/web/fundamentals", "title": "123Web Fundamentals - A handbook for best practices", "user": "ec4481d0-3f25-11e4-846a-091830ac5256", "tags": ["gogle", "test"]}' -H 'Content-Type: application/json' http://localhost:8080/dotmarks

-- curl -d '{"url": "http://blog.runscope.com/posts/supporting-api-focused-open-source-projects", "title": "Equipping API-focused Open Source Projects with Free API Debugging and Testing Tools — Runscope Blog", "user": "fce70d20-3f7d-11e4-8a23-45898ba0e0df", "source": "curl", "tags": ["api", "oos"]}' -H 'Content-Type: application/json' http://localhost:8080/dotmarks

