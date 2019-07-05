export enum VehicleType {
    Car,
    Motorcycle,
    Truck,
    Pickup
};

export const vehicleTypeDescription = {
    0: "Carro",
    1: "Moto",
    2: "Caminhão",
    3: "Caminhonete"
};

export const vehicleTypesLabels = [
    {
        type: VehicleType.Car,
        text: vehicleTypeDescription[VehicleType.Car],
    },
    {
        type: VehicleType.Motorcycle,
        text: vehicleTypeDescription[VehicleType.Motorcycle],
    },
    {
        type: VehicleType.Pickup,
        text: vehicleTypeDescription[VehicleType.Pickup],
    },
    {
        type: VehicleType.Truck,
        text: vehicleTypeDescription[VehicleType.Truck],
    }
];

export interface Vehicle {
    type: VehicleType;
    model: string;
    plate: string;
};

export enum OperationType {
    IN = 0,
    OUT = 1
};

export interface Operation {
    id?: number;
    createdAt?: string;
    updatedAt?: string;
    vehicle?: Vehicle;
    type?: OperationType;
    enteredAt?: string;
    exitedAt?: string;
}

export interface OperationValueResult {
    hours: number;
    minutes: number;
    seconds: number;
    value: number;
    totalHours: number;
    operationId: number;
    enteredAt: string;
    exitedAt: string;
}