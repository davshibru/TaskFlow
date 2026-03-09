#!/bin/bash

if [ -z "$1" ]; then
  echo "❌ Ошибка: Укажи путь/имя модуля."
  echo "💡 Пример использования: ./create_module.sh feature/auth"
  exit 1
fi

MODULE_PATH=$1
# Заменяем слеши на точки для пакета (feature/auth -> feature.auth)
PACKAGE_SUFFIX=${MODULE_PATH//\//.}
# Формируем базовый пакет
PACKAGE_NAME="com.davidshibru.taskflow.$PACKAGE_SUFFIX"

echo "🚀 Создаем модуль: $MODULE_PATH"
echo "📦 Пакет: $PACKAGE_NAME"

SRC_DIR="$MODULE_PATH/src/main/java/${PACKAGE_NAME//./\/}"
mkdir -p "$SRC_DIR"
mkdir -p "$MODULE_PATH/src/main/res"

# 2. Генерируем build.gradle.kts
cat <<EOF > "$MODULE_PATH/build.gradle.kts"
plugins {
    // Используем твои convention plugins (поменяй .application на .library для фичей)
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "$PACKAGE_NAME"
}

dependencies {
    // Здесь можно сразу прописать базовые зависимости, которые нужны во всех модулях
    // implementation(projects.core.ui)
}
EOF

# 3. Генерируем .gitignore для модуля
cat <<EOF > "$MODULE_PATH/.gitignore"
/build
EOF

# 4. Добавляем модуль в settings.gradle.kts (заменяем слеши на двоеточия)
GRADLE_INCLUDE=":${MODULE_PATH//\//:}"

# Проверяем, нет ли уже такого модуля в settings.gradle.kts
if grep -q "\"$GRADLE_INCLUDE\"" settings.gradle.kts; then
    echo "⚠️ Модуль уже прописан в settings.gradle.kts"
else
    echo "include(\"$GRADLE_INCLUDE\")" >> settings.gradle.kts
    echo "✅ Добавлено в settings.gradle.kts"
fi

echo "🎉 Готово! Сделай Sync Project with Gradle Files."