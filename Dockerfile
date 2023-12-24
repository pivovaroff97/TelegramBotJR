FROM khipu/openjdk17-alpine
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test.tg_JR_my_bot
ENV BOT_TOKEN=1231231233:AAFQMKgxdOzYymrRJQq-Yxys7u0PnK2Rdvo
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dtg-bot.username=${BOT_NAME}", "-Dtg-bot.token=${BOT_TOKEN}", "-jar", "/app.jar"]