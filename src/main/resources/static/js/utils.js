
const makeRequest = async (url, method, body) => {
  // Prepare the headers
  const headers = new Headers();
  // Retrieve the JWT token from localStorage
  const token = localStorage.getItem('jwtToken');

  if (token)
    headers.append('Authorization', `Bearer ${token}`);

  headers.append('Content-Type', 'application/json');

  try {
    const requestOptions = {
      method,
      headers,
    };

    if (method != 'GET') {
      requestOptions.body = JSON.stringify(body);
    }

    const response = await fetch(url, requestOptions);

      // Handle token expiration
      const expiredTokenHeader = response.headers.get("X-Expired-Token");
      if (expiredTokenHeader === 'true') {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userData')
        location.reload();
      }

      // Request was successful
      if (response.ok) {
        return await response.json();
      } else {
        // Request failed
        const e = await response.text()
        throw new Error(e);
      }
  } catch(error) {
        // Handle errors
        console.error(error);
  };
}

export {makeRequest};