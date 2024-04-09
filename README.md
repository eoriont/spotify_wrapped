# Spotify Wrapped Monorepo!

Welcome!

## Android App

Located in `SpotifyWrappedApp`

## Backend

### Install MySQL

For Mac, I recommend using Homebrew. In your terminal, copy the following command and let it run. This may take some time.

```shell
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

After this finishes, you're going to need to install `Maven` and `MySQL`.

```shell
brew install maven
brew install mysql
brew services start mysql
mysql -u root
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('root');
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
exit
```

Lastly, in the `backend` directory, copy `application.properties.example` as `application.properties`
and replace contents with your correct local sql credentials.