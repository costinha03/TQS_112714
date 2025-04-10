import http from 'k6/http';
    import { check, sleep } from 'k6';
    import { Rate } from 'k6/metrics';

    const errorRate = new Rate('errors');

    export const options = {
        stages: [
            { duration: '10s', target: 5 },
            { duration: '20s', target: 50 },
            { duration: '10s', target: 10 },
        ],
        thresholds: {
            http_req_duration: ['p(95)<1000'],
            errors: ['rate<0.1'],
        },
    };

    let restaurantId = null;
    let menuId = null;
    let reservationToken = null;

    export default function () {
        const baseUrl = 'http://localhost:8080/api';
        const headers = { 'Content-Type': 'application/json' };

        // 1. Create a restaurant
        const restaurantName = `Test Restaurant ${Date.now()}`;
        let createRestaurantResponse = http.post(
            `${baseUrl}/restaurants/create?name=${encodeURIComponent(restaurantName)}`,
            null,
            { headers }
        );

        let createRestaurantCheck = check(createRestaurantResponse, {
            'Create restaurant status is 201': (r) => r.status === 201,
        });
        errorRate.add(!createRestaurantCheck);

        if (createRestaurantResponse.status === 201) {
            restaurantId = createRestaurantResponse.json().id;

            // 2. Get all restaurants
            let getAllRestaurantsResponse = http.get(`${baseUrl}/restaurants`);
            check(getAllRestaurantsResponse, {
                'Get all restaurants status is 200': (r) => r.status === 200,
                'Get all restaurants returns array': (r) => Array.isArray(r.json()),
            });

            // 3. Create a menu
            const menuPayload = JSON.stringify({
                meatDish: { name: 'Steak', price: 15.99, type: 'MEAT' },
                fishDish: { name: 'Salmon', price: 12.99, type: 'FISH' },
                vegetarianDish: { name: 'Salad', price: 9.99, type: 'VEGETARIAN' },
            });

            let createMenuResponse = http.post(`${baseUrl}/menus`, menuPayload, { headers });
            let createMenuCheck = check(createMenuResponse, {
                'Create menu status is 201': (r) => r.status === 201,
            });
            errorRate.add(!createMenuCheck);

            if (createMenuResponse.status === 201) {
                menuId = createMenuResponse.json().id;

                // 4. Associate menu to a day
                let associateMenuResponse = http.post(
                    `${baseUrl}/restaurants/${restaurantId}/associate-menu?menuId=${menuId}&dayOfWeek=MONDAY`,
                    null,
                    { headers }
                );
                check(associateMenuResponse, {
                    'Associate menu status is 200': (r) => r.status === 200,
                });

                // 5. Create a reservation
                const reservationPayload = JSON.stringify({
                    studentId: '12345',
                    date: new Date().toISOString().split('T')[0],
                    timeSlot: 'DINNER',
                    dishType: 'MEAT',
                    restaurantId: restaurantId,
                    dayOfWeek: 'MONDAY',
                    totalPrice: 15.99
                });

                let createReservationResponse = http.post(`${baseUrl}/reservations`, reservationPayload, { headers });
                check(createReservationResponse, {
                    'Create reservation status is 201': (r) => r.status === 201,
                });

                if (createReservationResponse.status === 201) {
                    reservationToken = createReservationResponse.json().token;

                    // 6. Cancel reservation
                    if (reservationToken) {
                        let cancelReservationResponse = http.post(
                            `${baseUrl}/reservations/${reservationToken}/cancel`,
                            null,
                            { headers }
                        );
                        check(cancelReservationResponse, {
                            'Cancel reservation status is 200': (r) => r.status === 200,
                        });
                    }
                }
            }
        }

        // 7. Get weekly weather
        let getWeatherResponse = http.get(`${baseUrl}/weather`);
        check(getWeatherResponse, {
            'Get weather status is 200': (r) => r.status === 200,
            'Get weather returns array': (r) => Array.isArray(r.json()),
        });

        sleep(1);
    }