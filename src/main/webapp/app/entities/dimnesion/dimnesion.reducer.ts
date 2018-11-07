import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDimnesion, defaultValue } from 'app/shared/model/dimnesion.model';

export const ACTION_TYPES = {
  SEARCH_DIMNESIONS: 'dimnesion/SEARCH_DIMNESIONS',
  FETCH_DIMNESION_LIST: 'dimnesion/FETCH_DIMNESION_LIST',
  FETCH_DIMNESION: 'dimnesion/FETCH_DIMNESION',
  CREATE_DIMNESION: 'dimnesion/CREATE_DIMNESION',
  UPDATE_DIMNESION: 'dimnesion/UPDATE_DIMNESION',
  DELETE_DIMNESION: 'dimnesion/DELETE_DIMNESION',
  RESET: 'dimnesion/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDimnesion>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DimnesionState = Readonly<typeof initialState>;

// Reducer

export default (state: DimnesionState = initialState, action): DimnesionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_DIMNESIONS):
    case REQUEST(ACTION_TYPES.FETCH_DIMNESION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIMNESION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DIMNESION):
    case REQUEST(ACTION_TYPES.UPDATE_DIMNESION):
    case REQUEST(ACTION_TYPES.DELETE_DIMNESION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_DIMNESIONS):
    case FAILURE(ACTION_TYPES.FETCH_DIMNESION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIMNESION):
    case FAILURE(ACTION_TYPES.CREATE_DIMNESION):
    case FAILURE(ACTION_TYPES.UPDATE_DIMNESION):
    case FAILURE(ACTION_TYPES.DELETE_DIMNESION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_DIMNESIONS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIMNESION_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIMNESION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIMNESION):
    case SUCCESS(ACTION_TYPES.UPDATE_DIMNESION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIMNESION):
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

const apiUrl = 'api/dimnesions';
const apiSearchUrl = 'api/_search/dimnesions';

// Actions

export const getSearchEntities: ICrudSearchAction<IDimnesion> = query => ({
  type: ACTION_TYPES.SEARCH_DIMNESIONS,
  payload: axios.get<IDimnesion>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IDimnesion> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DIMNESION_LIST,
    payload: axios.get<IDimnesion>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDimnesion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIMNESION,
    payload: axios.get<IDimnesion>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDimnesion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIMNESION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDimnesion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIMNESION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDimnesion> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIMNESION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
