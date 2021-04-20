<#macro weatherItem weatherData>
    <div class="weather-item">
        <h2>${ weatherData.city }</h2>
        <img src="${ weatherData.imageLink }" alt="weather-icon">
        <h1>${ weatherData.temperature }°C</h1>
        <div>
            <p>Wind speed: ${ weatherData.windSpeed }mph</p>
            <p>Humidity: ${ weatherData.humidity }%</p>
            <p>Pressure: ${ weatherData.pressure }mb</p>
        </div>
    </div>
</#macro>