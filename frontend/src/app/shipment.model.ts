export type ShipmentModel = {
  id: number;
  originCity: {
    id: number;
    name: string;
    country: string;
    createdAt: string;
  };
  destinationCity: {
    id: number;
    name: string;
    country: string;
    createdAt: string;
  };
  freightType: string;
  estimatedDays: number;
  status: string;
  createdAt: string;
};

export type CreateShipmentModel = {
  originCityId: number;
  destinationCityId: number;
  freightType: string;
};
