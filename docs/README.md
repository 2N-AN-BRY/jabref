# JabRef Developer Documentation

The developer documentation is created using the Jekyll Theme [Just the Docs](https://just-the-docs.github.io/just-the-docs/).

## Local Development

For local development, follow the [Jekyll installation instructions](https://jekyllrb.com/docs/installation/).
Installing the latest version of ruby followed by `gem install bundler` should be enough.

Afterwards, run

```shell
bundle install
jekyll serve --livereload
```

and go to <http://localhost:4000/> in your browser.

On **Windows**, using a dockerized environment is recommended:

```shell
docker build . -t jrjekyll
docker run -p 4000:4000 -it --rm --volume="C:\git-repositories\jabref\docs":/srv/jekyll jrjekyll jekyll serve -H 0.0.0.0 -t
```

* With <kbd>Ctrl</kbd>+<kbd>C</kbd> you can stop the server (this is enabled by the `-it` switch).
* In case you get errors regarding `Gemfile.lock`, just delete `Gemfile.lock` and rerun.
* The current `Dockerfile` is hand-crafted.
  The [Jekyll Docker image](https://github.com/envygeeks/jekyll-docker#jekyll-docker) did not work end of 2022 (because Ruby was too new).

## Execute linting action

You can execute the linting action as if it was executed by GitHub by executing following command.

```shell
act --rm --platform ubuntu-latest=fwilhe2/act-runner:latest -W .github/workflows/tests.yml -j "markdown"
```

That command uses [act](https://github.com/nektos/act), which brings GitHub actions execution to the developer machine.

## Useful Tip

The official JabRef browser plugin works with Firefox, Chrome, Edge, and Vivaldi. It allows you to import bibliographic references directly from websites into JabRef with a single click.

