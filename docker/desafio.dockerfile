FROM nginx:latest
MAINTAINER Bruno Medeiros <bmdefreitas@gmail.com>
COPY /docker/config/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 22 8000
ENTRYPOINT ["nginx"]
CMD ["-g", "daemon off;"]