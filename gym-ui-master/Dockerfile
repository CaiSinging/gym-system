FROM node:alpine as builder
WORKDIR /app
COPY . /app
RUN npm config set registry https://registry.npm.taobao.org \
 && npm install -g create-react-app \
 && npm install \
 && npm run build

FROM nginx:alpine as prod
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/build/* /usr/share/nginx/html/
