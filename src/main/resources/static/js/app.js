// Аналогично с JS, т.к. я не знаю этот язык вообще. (Даже Kotlin больше знаю :DDD)
// Ждем, пока страница загрузится
document.addEventListener("DOMContentLoaded", () => {
    // Находим наш контейнер
    const container = document.getElementById("js-container");

    // 1. Создаем кнопку
    const button = document.createElement("button");
    button.innerText = "Показать числа от 0 до 10";
    button.style.padding = "10px 20px";
    button.style.fontSize = "16px";
    button.style.cursor = "pointer";
    button.style.borderRadius = "8px";
    button.style.border = "none";
    button.style.backgroundColor = "#3498db";
    button.style.color = "white";
    
    // 2. Добавляем кнопку на страницу
    container.appendChild(button);

    // 3. Создаем скрытый список, куда будем выводить цифры
    const list = document.createElement("ul");
    list.style.listStyleType = "none";
    list.style.padding = "0";
    list.style.fontSize = "24px";
    list.style.display = "none"; // Сначала скрыт
    container.appendChild(list);

    // 4. Вешаем событие клика на кнопку
    button.addEventListener("click", () => {
        // Очищаем список перед заполнением
        list.innerHTML = "";
        
        // Запускаем наш цикл
        for (let i = 0; i <= 10; i++) {
            const li = document.createElement("li");
            li.innerText = "Число: " + i;
            li.style.color = i % 2 === 0 ? "#2c3e50" : "#e74c3c"; // Четные синие, нечетные красные
            list.appendChild(li);
        }

        // Показываем список и меняем текст кнопки
        list.style.display = "block";
        button.innerText = "Обновить числа (кликни еще раз)";
    });
});