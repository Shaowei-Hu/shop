import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IToy, defaultValue } from 'app/shared/model/toy.model';

export const ACTION_TYPES = {
  SEARCH_TOYS: 'toy/SEARCH_TOYS',
  FETCH_TOY_LIST: 'toy/FETCH_TOY_LIST',
  FETCH_TOY: 'toy/FETCH_TOY',
  CREATE_TOY: 'toy/CREATE_TOY',
  UPDATE_TOY: 'toy/UPDATE_TOY',
  DELETE_TOY: 'toy/DELETE_TOY',
  RESET: 'toy/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IToy>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ToyState = Readonly<typeof initialState>;

// Reducer

export default (state: ToyState = initialState, action): ToyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TOYS):
    case REQUEST(ACTION_TYPES.FETCH_TOY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TOY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TOY):
    case REQUEST(ACTION_TYPES.UPDATE_TOY):
    case REQUEST(ACTION_TYPES.DELETE_TOY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TOYS):
    case FAILURE(ACTION_TYPES.FETCH_TOY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TOY):
    case FAILURE(ACTION_TYPES.CREATE_TOY):
    case FAILURE(ACTION_TYPES.UPDATE_TOY):
    case FAILURE(ACTION_TYPES.DELETE_TOY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TOYS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TOY):
    case SUCCESS(ACTION_TYPES.UPDATE_TOY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TOY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/toys';
const apiSearchUrl = 'api/_search/toys';

// Actions

export const getSearchEntities: ICrudSearchAction<IToy> = query => ({
  type: ACTION_TYPES.SEARCH_TOYS,
  payload: axios.get<IToy>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IToy> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TOY_LIST,
    payload: axios.get<IToy>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IToy> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TOY,
    payload: axios.get<IToy>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IToy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TOY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IToy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TOY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IToy> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TOY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
