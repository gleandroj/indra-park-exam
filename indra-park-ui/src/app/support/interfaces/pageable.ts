export interface Pageable<T> {
    content?: T[];
    empty?: boolean;
    size?: number;
    totalPages?: number;
    totalElements?: number;
}