import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export abstract class AbstractService<T> {

    protected baseURL = environment.API_URL;

    protected abstract get resourceURL();

    constructor(protected http: HttpClient) {}

    public all(filters?: any): Observable<T[]> {
        const mapped = this.buildParameter(filters);
        return this.http.get<T[]>(
            `${this.baseURL}/${this.resourceURL}${mapped}`
        );
    }

    public find(id): Observable<T> {
        return this.http.get<T>(`${this.baseURL}/${this.resourceURL}/${id}`);
    }

    public save(entity: T | any): Observable<T> {
        let response = null;

        if (entity.id == null) {
            response = this.http.post<T>(`${this.baseURL}/${this.resourceURL}`, entity);
        } else {
            response = this.http.put<T>(`${this.baseURL}/${this.resourceURL}/${entity.id}`, entity);
        }

        return response;
    }

    public delete(id: number): Observable<T> {
        return this.http.delete<any>(`${this.baseURL}/${this.resourceURL}/${id}`);
    }

    protected buildParameter(data: any) {
        const _filter = Object.keys(data).map(k => {
            const value = this.formatValue(data[k]);
            return value ? `${k}=${value}` : null;
        });
        const filter = _filter.filter(f => f != null).join('&');
        return filter && filter.length > 0 ? `?${filter}` : '';
    }

    protected formatValue(value) {
        if (value instanceof Date) {
            return value.toISOString();
        } else {
            return value;
        }
    }
}
