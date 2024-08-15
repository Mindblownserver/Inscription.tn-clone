import axios, { AxiosInstance, AxiosResponse } from 'axios';

class MyAPI {
  private instance: AxiosInstance;

  constructor(baseURL: string) {
    this.instance = axios.create({
      baseURL,
    });

    // interceptor for adding Auth headers
    this.instance.interceptors.request.use(config => {
      const token = localStorage.getItem('accessToken');
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
        console.log('Adding Authorization header:', `Bearer ${token}`);
      } else {
        console.log('No token found in localStorage');
      }
      return config;
    }, error => {
      return Promise.reject(error);
    });

    // interceptor for handling refresh token
    // Response Interceptor: Handle token refresh
    this.instance.interceptors.response.use(
      response => response,
      async error => {
        const { response } = error;
        const originalRequest = error.config;

        // Check for token expiration
        if (response && response.status === 401 && response.data === 'Token expired') {
          const refreshToken = localStorage.getItem('refreshToken');
          if (refreshToken) {
            try {
              const refreshResponse = await axios.post(`${baseURL}/auth/refresh`, {
                refreshToken
              });

              const { accessToken, newRefreshToken } = refreshResponse.data;

              // Save the new tokens
              localStorage.setItem('accessToken', accessToken);
              localStorage.setItem('refreshToken', newRefreshToken);

              // Retry the original request with the new access token
              originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;
              return this.instance.request(originalRequest);
            } catch (refreshError) {
              console.error('Token refresh failed:', refreshError);
              // Optionally, you might want to redirect to login or handle the error
              return Promise.reject(refreshError);
            }
          } else {
            console.log('No refresh token found in localStorage');
            // Optionally, you might want to redirect to login or handle the error
          }
        }

        return Promise.reject(error);
      }
    );
  }
  public getInscriptionByCin<T>(cin: string):Promise<AxiosResponse<T>>{
    try{
      return this.instance.get<T>("/api/inscription/"+cin);
    }catch(error){
      console.error("Error performing getInscriptionByCin operation",error);
      throw error;
    }
  }

  public getStudentByCin<T>(cin: string):Promise<AxiosResponse<T>>{
    try{
      return this.instance.get<T>("/api/etudiant/"+cin);
    }catch(error){
      console.error("Error performing getStudentByCin operation",error);
      throw error;
    }
  }

  public async register(username: string, password: string): Promise<void> {
    try {
      await this.instance.post('/auth/register', { username, password });
    } catch (error) {
      console.error("Error performing registration operation", error);
      throw error;
    }
  }

  public async login(username: string, password: string): Promise<void> {
    try {
      const response = await this.instance.post<{accessToken: string, refreshToken: string, userRole:string}>('/auth/login', { username, password });
      console.log(response.data)
      localStorage.setItem('accessToken', response.data.accessToken);
      localStorage.setItem('refreshToken', response.data.refreshToken);
      localStorage.setItem('role', response.data.userRole);
      
    } catch (error) {
      console.error("Error performing login operation", error);
      throw error;
    }
  }
  public logout():void{
    // Clear tokens from local storage
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('role');
  }
}
export const myApi  =new MyAPI("http://localhost:8080");