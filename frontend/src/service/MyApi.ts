import axios, { AxiosInstance, AxiosResponse } from 'axios';
import moment from 'moment';

class MyAPI {
  private instance: AxiosInstance;

  constructor(baseURL: string) {
    this.instance = axios.create({
      baseURL,
      transformResponse: [function (data: string): any {
        // Parse JSON response with date reviver function
        return JSON.parse(data, (key, value) => {
          if (typeof value === 'string' && value.match(/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.\d{3}[+-]\d{2}:\d{2}$/)) {
            const date = moment.utc(value).toDate();
            return moment(date).format("DD/MM/YYYY"); // Convert to JavaScript Date object using Moment.js
          }
          return value;
        });
      }],
    });
  }
  public getInscriptionByCin<T>(cin: string):Promise<AxiosResponse<T>>{
    try{
      return this.instance.get<T>("/api/inscription/"+cin);
    }catch(error){
      console.error("Error performing getInscriptionByCin operation",error);
      throw error;
    }
  }
}
export const myApi  =new MyAPI("http://localhost:8080");